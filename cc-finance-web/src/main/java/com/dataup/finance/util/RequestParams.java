package com.dataup.finance.util;

import java.util.HashMap;

public class RequestParams {

	private HashMap<Object, Object> values = new HashMap<Object, Object>();

	public Object getValue(String param) {
		return values.get(param);
	}

	public void setValue(String param, Object value) {
		this.values.put(param, value);
	}

	public HashMap<Object, Object> getAll(){
		return this.values;
	}
}
