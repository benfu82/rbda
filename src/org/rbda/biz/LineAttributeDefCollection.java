package org.rbda.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Pipeline Attribute Defined Object Collection
 * 
 * @author fujunwei
 * 
 */
public class LineAttributeDefCollection {
	private List<LineAttributeDef> lineAttrDefs;

	public LineAttributeDefCollection() {
		lineAttrDefs = new ArrayList<LineAttributeDef>();
	}

	public void addLineAttributeDef(LineAttributeDef lineAttrDef) {
		if (lineAttrDef == null)
			return;
		lineAttrDefs.add(lineAttrDef);
	}

	public int getCount() {
		return lineAttrDefs == null ? 0 : lineAttrDefs.size();
	}

	public LineAttributeDef getLineAttributeDefAt(int index){
		if(index<0||lineAttrDefs==null||index>=lineAttrDefs.size()) return null;
		
		return lineAttrDefs.get(index);
	}
	
	
	public LineAttributeDef getLineAttributeDef(int attrID) {
		LineAttributeDef found = null;
		for (LineAttributeDef attrDef : lineAttrDefs) {
			if (attrDef.getId() == attrID) {
				found = attrDef;
				break;
			}
		}
		return found;
	}

	/**
	 * Get the all pipeline attribute defined object
	 * 
	 * @return
	 */
	public LineAttributeDef[] getAll() {
		return lineAttrDefs==null?null:lineAttrDefs.toArray(new LineAttributeDef[0]);
	}
	
	public boolean isExisted(String attrName){
		boolean isExisted = false;
		for (LineAttributeDef attrDef : lineAttrDefs) {
			if (attrDef.getName()==attrName) {
				isExisted = true;
				break;
			}
		}
		
		return isExisted;
	}
}
