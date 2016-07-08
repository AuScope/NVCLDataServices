-- NVCL create WebService user script
-- Use this script to create a user account for all web service PCs which require access to the nvcl DB publishing views.  This is a limited read only account.
-- Usually you will only need 1 account of this type used by all Web Servers
-- replace the 4 occurences of <username> with the desired username (eg WebServers) and <password> with their password 

create user <username> identified by <password>;

grant connect to <username>;

call NVCL.SETUPNVCLVIEWER('<username>');
call NVCL.SETUPWEBSERVICE('<username>');