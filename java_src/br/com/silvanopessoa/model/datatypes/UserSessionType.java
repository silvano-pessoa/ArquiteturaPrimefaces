package br.com.silvanopessoa.model.datatypes;

public enum UserSessionType {

	FUNC_ID("unicom.session.funcid"),
	
	ACCESSLIST("unicom.session.acesslist"),
	
	PERMISSION_PAGES("unicom.session.permpages"),
	
	TITLE_PAGES("unicom.session.titpages"),
	
	URL_REDIRECT("unicom.session.urlredirect"),
	
	LOCALE("unicom.session.locale"),
	
	USER_NAME("unicom.session.username");
	
	private String key;
	
	private UserSessionType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
