package org.auscope.nvcl.server.vo;

/**
 * This URLParamVo consists of URL parameters submitted by the user.
 * 
 * @author Florence Tan
 */



public class TsgParamVo {

		
	    private String email;
		private String datasetid;
		private String linescan;
		
		public String getEmail() {
		  return email;
		}
		 
		public void setEmail(String email) {
			this.email = email;
		}
		
		public void setDatasetid(String datasetid) {
			this.datasetid = datasetid;
		}

		public String getDatasetid() {
			  return datasetid;
		}

		public String getLinescan() {
		  return linescan;
		}
			 
		public void setLinescan(String linescan) {
			this.linescan = linescan;
		}
		

}
