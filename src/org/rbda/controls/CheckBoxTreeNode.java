package org.rbda.controls;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxTreeNode extends DefaultMutableTreeNode {
	private boolean isSelected = false;
	private boolean isChecked = false;
	private int status = 0;

	/**
	 * Creates a tree node that has no parent and no children, but which allows
	 * children.
	 */
	public CheckBoxTreeNode() {
		this(null);
	}

	/**
	 * Creates a tree node with no parent, no children, but which allows
	 * children, and initializes it with the specified user object.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 */
	public CheckBoxTreeNode(Object userObject) {
		this(userObject, true);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 */
	public CheckBoxTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
//		System.out.println(String.format(
//				"%s selected status %s and isChecked %s", userObject,
//				this.isSelected, this.isChecked));

		// update children node's selected status recursively
		if (isChecked)
			return;
		isChecked = true;
		Enumeration children = this.children();
		if (children != null) {
			// check wether any child is checked, if any one is checked, don't
			// traverse children.
			boolean hasChildChecked = false;
			while (children.hasMoreElements()) {
				CheckBoxTreeNode childNode = (CheckBoxTreeNode) children
						.nextElement();
//				System.out.println(String.format("child %s checked status %s",
//						childNode, childNode.isChecked));
				if (childNode.isChecked) {
					hasChildChecked = true;
					break;
				}
			}
			if (!hasChildChecked) {
				children = this.children();
				while (children.hasMoreElements()) {
					CheckBoxTreeNode childNode = (CheckBoxTreeNode) children
							.nextElement();
					// if(childNode.isChecking) continue;
					childNode.setSelected(isSelected);
				}
			}
		}

		// update parent node's selected status recursively
		CheckBoxTreeNode parentNode = (CheckBoxTreeNode) this.getParent();
		if (parentNode != null) {
			int index = 0;
			children = parentNode.children();
//			System.out.println(String
//					.format("traverse %s children", parentNode));
			while (children.hasMoreElements()) {
				CheckBoxTreeNode childNode = (CheckBoxTreeNode) children
						.nextElement();
//				System.out.println(String.format("child %s selected status %s",
//						childNode, childNode.isSelected));
				if (!childNode.isSelected())
					break;
				index++;
			}
//			System.out.println("**end**");
			boolean bSelected = (index == parentNode.getChildCount()) ? true
					: false;
			parentNode.setSelected(bSelected);
		}
		isChecked = false;
	}
}
