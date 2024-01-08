/****** Object:  UserDefinedFunction [dbo].[GETDOMAINDATAFUNC]    Script Date: 3/01/2024 3:10:15 PM ******/
DROP FUNCTION [dbo].[GETDOMAINDATAFUNC]
GO

/****** Object:  UserDefinedFunction [dbo].[GETDOMAINDATAFUNC]    Script Date: 3/01/2024 3:10:15 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[GETDOMAINDATAFUNC] 
(
	@v_domainlog_id varchar(max)
)
RETURNS 
@Temp TABLE
       (samplenumber   integer INDEX IX1 CLUSTERED,
        startvalue     numeric(18,5),
        endvalue       numeric(18,5),
        samplename     varchar(4000))
AS
BEGIN
	 DECLARE
         @subdomid varchar(64)

      SELECT @subdomid = DOMAINLOGS.ISSUBDOMAINOFLOG_ID
      FROM dbo.DOMAINLOGS
      WHERE DOMAINLOGS.LOG_ID = @v_domainlog_id

      IF (@subdomid IS NULL)
        insert into @Temp SELECT samplenumber,startvalue,endvalue,samplename FROM DOMAINLOGDATA WHERE LOG_ID=@v_domainlog_id ORDER BY samplenumber
      ELSE 
         insert into @Temp SELECT subdom.samplenumber,min(maindom.startvalue) as startvalue,max(maindom.endvalue) as endvalue,subdom.samplename FROM dbo.GETDOMAINDATAFUNC(@subdomid) maindom inner join domainlogdata subdom on maindom.samplenumber BETWEEN subdom.startvalue ANd subdom.endvalue WHERE subdom.log_id=@v_domainlog_id Group BY subdom.samplenumber,subdom.samplename ORDER by subdom.samplenumber
	
	RETURN 
END
GO