package nvd.cve.json;

public class ComVulEx {
	private String CVE_data_type;
	private String CVE_data_format;
	private String CVE_version;
	private String CVE_data_numberOfCVEs;
	private String CVE_data_timestamp;

	private CVE_Item CVE_Items;
	
	
	public String getCVE_data_type() {
		return CVE_data_type;
	}
	public void setCVE_data_type(String cVE_data_type) {
		CVE_data_type = cVE_data_type;
	}
	public String getCVE_data_format() {
		return CVE_data_format;
	}
	public void setCVE_data_format(String cVE_data_format) {
		CVE_data_format = cVE_data_format;
	}
	public String getCVE_version() {
		return CVE_version;
	}
	public void setCVE_version(String cVE_version) {
		CVE_version = cVE_version;
	}
	public String getCVE_data_numberOfCVEs() {
		return CVE_data_numberOfCVEs;
	}
	public void setCVE_data_numberOfCVEs(String cVE_data_numberOfCVEs) {
		CVE_data_numberOfCVEs = cVE_data_numberOfCVEs;
	}
	public String getCVE_data_timestamp() {
		return CVE_data_timestamp;
	}
	public void setCVE_data_timestamp(String cVE_data_timestamp) {
		CVE_data_timestamp = cVE_data_timestamp;
	}
	public CVE_Item getCVE_Items() {
		return CVE_Items;
	}
	public void setCVE_Items(CVE_Item cVE_Items) {
		CVE_Items = cVE_Items;
	}
}
