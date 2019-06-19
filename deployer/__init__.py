import logging
import os
import boto3


def get_logger(name):
    handler = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s %(name)-8s %(levelname)-8s %(message)s')
    handler.setFormatter(formatter)
    logger = logging.getLogger(name)
    logger.addHandler(handler)


# Set the logging level
    debug_level = get_setting('LOG_LEVEL', 'DEBUG')
    if debug_level.upper() == 'DEBUG':
        logger.setLevel(logging.DEBUG)
    elif debug_level.upper() == 'INFO':
        logger.setLevel(logging.INFO)
    elif debug_level.upper() == 'WARN':
        logger.setLevel(logging.WARN)
    elif debug_level.upper() == 'ERROR':
        logger.setLevel(logging.ERROR)
    else:
        logger.setLevel(logging.INFO)

# Raise the logging level for these modules to reduce noise
    logging.getLogger("urllib3").setLevel(logging.WARNING)
    logging.getLogger("github3").setLevel(logging.WARNING)
    logging.getLogger("botocore").setLevel(logging.WARNING)
    return logger


# Moving the env setup to app
def get_setting(param_name, default=None):
    """
    This function looks for an upper case environment variable and then defaults
    to checking AWS SSM Parameters (lower case)
    :param param_name:
    :param default:
    :return:
    """
    value = None

    try:
        value = os.environ.get(param_name.upper(), get_ssm_parameter(param_name))
    except IndexError as e:
        value = default
    return value


def get_ssm_parameter(param_name):
    """
    This function reads a secure parameter from AWS' SSM service.
    The request must be passed a valid parameter name, as well as
    temporary credentials which can be used to access the parameter.
    The parameter's value is returned.
    """
    # Create the SSM Client
    ssm = boto3.client('ssm')
    # Get the requested parameter
    response = ssm.get_parameters(
        Names=[
            param_name,
        ],
        WithDecryption=True
    )

    # Store the credentials in a variable
    try:
        credentials = response['Parameters'][0]['Value']
    except Exception as e:
        logging.debug('No AWS SSM Parameter found for: {}\n{}'.format(param_name, e))
        raise

    return credentials

