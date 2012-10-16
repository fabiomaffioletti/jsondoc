package org.jsondoc.core;

public class ReturnTypePojo {
	private Class<?> clazz;
	private boolean multiple;
	private String object;
	private Class<?> mapKeyObject;
	private Class<?> mapValueObject;

	public ReturnTypePojo() {
		super();
	}

	public Class<?> getMapKeyObject() {
		return mapKeyObject;
	}

	public void setMapKeyObject(Class<?> mapKeyObject) {
		this.mapKeyObject = mapKeyObject;
	}

	public Class<?> getMapValueObject() {
		return mapValueObject;
	}

	public void setMapValueObject(Class<?> mapValueObject) {
		this.mapValueObject = mapValueObject;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	@Override
	public String toString() {
		return "ReturnTypePojo [clazz=" + clazz + ", multiple=" + multiple + ", object=" + object + ", mapKeyObject=" + mapKeyObject + ", mapValueObject=" + mapValueObject + "]";
	}

}
