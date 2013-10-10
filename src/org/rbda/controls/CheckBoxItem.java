package org.rbda.controls;

public class CheckBoxItem {
	private boolean isChecked;
	private Object userObject;

	public CheckBoxItem(Object userObject) {
		this(userObject, false);
	}

	public CheckBoxItem(Object userObject, boolean isChecked) {
		this.isChecked = isChecked;
		this.userObject = userObject;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Object getUserObject() {
		return userObject;
	}

	public String toString() {
		return userObject != null ? userObject.toString() : "NULL";
	}
}
