package nvd.cve.json;

import java.util.ArrayList;
import java.util.List;

public class CVE_Item {
	List<CVE> cve=new ArrayList<CVE>();

	
	public List<CVE> getCve() {
		return cve;
	}

	public void setCve(List<CVE> cve) {
		this.cve = cve;
	}
	
	
}
