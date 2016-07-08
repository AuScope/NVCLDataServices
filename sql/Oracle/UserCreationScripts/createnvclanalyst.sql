-- NVCL create Analyst script
-- Use this script to create a user account for users who require write access to the nvcl DB
-- Usually this will only be the HyLogging geologist 
-- replace the 3 occurences of <username> with the desired username (eg JohnSmith) and <password> with their password 

create user <username> identified by <password>;

grant connect to <username>;

call NVCL.SETUPNVCLANALYST('<username>');
