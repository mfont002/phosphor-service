
# Deploys aws resources from ini files.


About - 
All the methods in the boto3 api use key/value pair structures for input parameters.  
This makes it easy to structure an ini file to create almost any object with boto3 since an ini file can be easily 
converted to a dictionary of key/value pairs and passed to boto3.  This should provide more simplicity than using 
yaml or json templates to create cloud resources as they often require different and sometime complex semantics and 
rules to create resources.

Running - 
Monitor.py: Starts the deployment tool.  Runs as a background process monitoring a given directory for new or modified 
ini files. Defaults to "in-files" dir if no dir specified.  To create an aws resource copy one or more ini files 
to the "in-files" dir. Directory "config" contains a set of example ini files for creating an rds, lambda and its 
dependencies, s3 bucket and ssm. Other aws resources can be created by adding ini files. After an ini file is processed
and the aws resource created, the result will be saved to the out-processed-files dir. If there is an error
when creating, the result will be saved to the out-error-files.

Does not handle dependent resources, i.e., when creating a lambda resource, if an s3 bucket is needed it must exist before
trying to create the lambda resource. Process the s3 bucket ini file 1st if it does not exist.

Structuring an ini file to create an aws resource: see "example.ini" file in config dir for guidance.




