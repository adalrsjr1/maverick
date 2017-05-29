package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom

class HttpSockShopSymptom implements IMaverickSymptom {

	public static final HttpSockShopSymptom EMPTY_HTTP_SOCK_SHOP_SYMPTOM = new HttpSockShopSymptom() 
	
	private IMaverickSymptom symptom
	private HttpSockShopSymptom() { }

	@Override
	public String getContainerId() {
		return symptom.getContainerId()
	}

	@Override
	public String getContainerName() {
		return symptom.getContainerName()
	}

	@Override
	public String getSource() {
		return symptom.getSource()
	}

	@Override
	public Object getLogMessage() {
		return symptom.getLogMessage()
	}

	@Override
	public IMaverickSymptom getEmpty() {
		return EMPTY_HTTP_SOCK_SHOP_SYMPTOM
	}
}
