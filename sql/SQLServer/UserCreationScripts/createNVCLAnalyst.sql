-- NVCL create Analyst script
-- Use this script to create a user account for users who require write access to the nvcl DB
-- Usually this will only be the HyLogging geologist 
-- replace the 3 occurences of <username> with the desired username (eg JohnSmith) and <password> with their password 
CREATE LOGIN [<username>] WITH PASSWORD='<password>', DEFAULT_DATABASE=[NVCL]
GO
USE [NVCL]
GO
CREATE USER [<username>] FOR LOGIN [<username>] WITH DEFAULT_SCHEMA=[dbo];
GO
EXEC sp_addrolemember N'NVCLANALYST', N'<username>'
GO
