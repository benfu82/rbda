package org.rbda.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.rbda.controls.CheckBoxTableCellRender;
import org.rbda.controls.InfoDialog;
import org.rbda.controls.OKCancelDialog;

import org.rbda.equation.Equation;
import org.rbda.equation.EquationCollection;
import org.rbda.equation.IEquation;
import org.rbda.resource.TextResourceBundle;

import org.rbda.biz.LineAttribute;
import org.rbda.biz.LineAttributeDef;
import org.rbda.biz.LineAttributeDefCollection;
import org.rbda.biz.Pipeline;
import org.rbda.biz.Section;

import sun.swing.table.DefaultTableCellHeaderRenderer;

public class PipelineAttributePanel extends JPanel implements ItemListener,
		ListSelectionListener, ActionListener {
	private Pipeline pipeline;
	private LineAttributeDefCollection lineAttrDefs;
	private EquationCollection equations;
	private JList<LineAttributeDef> lineAttrListBox; // Line Attribute ListBox
	private AttributeTableModel attrTableModel;
	private InfoDialog equationAttrDialog;
	private LineAttributeFilterPanel lineAttrFilterPanel;
	private OKCancelDialog lineAttrFilterDialog;
	private JComboBox<String> comboxFilter; // Filter Name ComboBox
	private JCheckBox chkAutoGen;

	private LineAttributeDef[] curAttrDefs;

	private static final int MARGIN = ControlConstants.MARGIN;

	public PipelineAttributePanel(Pipeline pipeline,
			LineAttributeDefCollection lineAttrDefs,
			EquationCollection equations) {
		this.pipeline = pipeline;
		this.lineAttrDefs = lineAttrDefs;
		this.equations = equations;
		init();
	}

	public void showDialog() {
		this.setVisible(true);
	}

	public void save() {
		attrTableModel.save();
	}

	private void init() {
		// init top pane
		JPanel leftUpPane = initLeftUpPane();
		JPanel rightUpPane = initRightUpPane();
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.add(Box.createHorizontalStrut(10));
		topPane.add(leftUpPane);
		topPane.add(Box.createHorizontalStrut(10));
		topPane.add(rightUpPane);

		//
		JPanel attrEditPane = initAttrEditPanel();
		JPanel lineChartPane = initLineChartPanel();

		//
		lineAttrFilterDialog = createLineAttrDefDialog();

		//
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(Box.createVerticalStrut(MARGIN * 2));
		this.add(topPane);
		this.add(Box.createHorizontalStrut(MARGIN));
		this.add(attrEditPane);
		// this.add(lineChartPane);
	}

	private JPanel initLeftUpPane() {
		// initalize distance description pane
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.setBorder(new TitledBorder(new EtchedBorder(),
				TextResourceBundle.getInstance().getString("Distance Posts"),
				TitledBorder.LEFT, TitledBorder.TOP));

		JTextField txtStart = new JTextField(15);
		txtStart.setEditable(false);
		txtStart.setText(String.valueOf(pipeline.getStart()));

		JTextField txtEnd = new JTextField(15);
		txtEnd.setEditable(false);
		txtEnd.setText(String.valueOf(pipeline.getEnd()));

		JPanel lblPane = new JPanel();
		lblPane.setLayout(new BoxLayout(lblPane, BoxLayout.PAGE_AXIS));
		lblPane.add(new JLabel(TextResourceBundle.getInstance().getString(
				"Start")));
		lblPane.add(Box.createVerticalStrut(20));
		lblPane.add(new JLabel(TextResourceBundle.getInstance()
				.getString("End")));

		JPanel txtPane = new JPanel();
		txtPane.setLayout(new BoxLayout(txtPane, BoxLayout.PAGE_AXIS));
		txtPane.add(txtStart);
		txtPane.add(txtEnd);

		JPanel unitPane = new JPanel();
		unitPane.setLayout(new BoxLayout(unitPane, BoxLayout.PAGE_AXIS));
		unitPane.add(new JLabel(TextResourceBundle.getInstance()
				.getString("km")));
		unitPane.add(Box.createVerticalStrut(20));
		unitPane.add(new JLabel(TextResourceBundle.getInstance()
				.getString("km")));

		topPane.add(Box.createHorizontalStrut(MARGIN));
		topPane.add(lblPane);
		topPane.add(txtPane);
		topPane.add(unitPane);
		topPane.add(Box.createHorizontalStrut(20));

		//
		JPanel downPane = new JPanel();
		downPane.setLayout(new BoxLayout(downPane, BoxLayout.PAGE_AXIS));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setHgap(0);
		JPanel moduleAttrPane = new JPanel(flowLayout);
		moduleAttrPane.setBorder(BorderFactory.createEmptyBorder());
		JButton btnModuleAttr = new JButton(TextResourceBundle.getInstance()
				.getString("Module-Attribute Relationships"));
		btnModuleAttr.setActionCommand("ModuleAttribute");
		btnModuleAttr.addActionListener(this);
		moduleAttrPane.add(btnModuleAttr);

		FlowLayout flowLayout2 = new FlowLayout(FlowLayout.LEFT);
		flowLayout2.setHgap(0);
		flowLayout2.setVgap(0);
		JPanel autoGenPane = new JPanel(flowLayout2);
		chkAutoGen = new JCheckBox(TextResourceBundle.getInstance().getString(
				"Station Auto-generate"));
		autoGenPane.add(chkAutoGen);
		autoGenPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				BorderFactory.createEmptyBorder()));
		downPane.add(moduleAttrPane);
		downPane.add(autoGenPane);

		// add into the container
		JPanel leftUpPane = new JPanel();
		leftUpPane.setLayout(new BoxLayout(leftUpPane, BoxLayout.PAGE_AXIS));
		leftUpPane.add(topPane);
		leftUpPane.add(downPane);
		return leftUpPane;
	}

	private JPanel initRightUpPane() {
		// init line attributes listbox
		lineAttrListBox = new JList<LineAttributeDef>();
		lineAttrListBox.addListSelectionListener(this);
		lineAttrListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lineAttrListBox.setListData(lineAttrDefs.getAll());
		BorderLayout lineAttrLayout = new BorderLayout();
		lineAttrLayout.setHgap(MARGIN * 2);
		JPanel lineAttrPane = new JPanel(lineAttrLayout);
		lineAttrPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0,
				MARGIN * 2));
		lineAttrPane.add(new JScrollPane(lineAttrListBox), BorderLayout.CENTER);

		// init filter pane
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setHgap(0);
		JPanel pnlFilter = new JPanel(flowLayout);
		JLabel lblFilter = new JLabel(TextResourceBundle.getInstance()
				.getString("Filter"));
		comboxFilter = new JComboBox<String>();
		comboxFilter
				.addItem(TextResourceBundle.getInstance().getString("None"));
		String[] filterNames = pipeline.getAttributeFilterNames();
		if (filterNames != null) {
			for (String filterName : filterNames) {
				comboxFilter.addItem(filterName);
			}
		}
		comboxFilter.addItemListener(this);

		JButton btnNew = new JButton("New");
		btnNew.setActionCommand("NewFilter");
		btnNew.addActionListener(this);
		JButton btnEdit = new JButton("Edit");
		btnEdit.setActionCommand("EditFilter");
		btnEdit.addActionListener(this);
		JButton btnRemove = new JButton("Remove");
		btnRemove.setActionCommand("RemoveFilter");
		btnRemove.addActionListener(this);
		pnlFilter.add(lblFilter);
		pnlFilter.add(comboxFilter);
		pnlFilter.add(btnNew);
		pnlFilter.add(btnEdit);
		pnlFilter.add(btnRemove);

		// add them into the container
		BorderLayout layout = new BorderLayout();
		layout.setHgap(MARGIN * 2);
		JPanel rightUpPane = new JPanel(layout);
		rightUpPane.add(pnlFilter, BorderLayout.NORTH);
		rightUpPane.add(lineAttrPane, BorderLayout.CENTER);
		return rightUpPane;
	}

	private JPanel initAttrEditPanel() {
		attrTableModel = new AttributeTableModel(this.pipeline, true);
		JTable table = new JTable(attrTableModel);
		DefaultTableCellHeaderRenderer headerRender = new DefaultTableCellHeaderRenderer();
		headerRender.setHorizontalAlignment(JLabel.CENTER);
		table.getTableHeader().setDefaultRenderer(headerRender);
		AttributeTableCellEditor cellEditor = new AttributeTableCellEditor(
				attrTableModel);
		cellEditor.addCellEditorListener(table);
		table.setDefaultEditor(Object.class, cellEditor);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		BorderLayout borderLayout = new BorderLayout();
		JPanel panel = new JPanel(borderLayout);
		panel.setBorder(BorderFactory.createEmptyBorder(0, MARGIN * 2, 0,
				MARGIN * 2));
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		return panel;
	}

	private JPanel initLineChartPanel() {
		JPanel panel = new JPanel();

		return panel;
	}

	// create line attribute edit dialog
	private OKCancelDialog createLineAttrDefDialog() {
		lineAttrFilterPanel = new LineAttributeFilterPanel(
				this.lineAttrDefs.getAll());
		lineAttrFilterDialog = new OKCancelDialog((Frame) null,
				lineAttrFilterPanel, TextResourceBundle.getInstance()
						.getString("Line Attribute Filter"), true);
		lineAttrFilterDialog
				.setDefaultCloseOperation(OKCancelDialog.HIDE_ON_CLOSE);
		lineAttrFilterDialog.setValidChecker(lineAttrFilterPanel);
		lineAttrFilterDialog.setLocationRelativeTo(this);
		lineAttrFilterDialog.setSize(new Dimension(
				ControlConstants.DIALOG_WIDTH, ControlConstants.DIALOG_HEIGHT));
		return lineAttrFilterDialog;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			String selected = (String) e.getItem();
			int[] attrIDs = pipeline.getAttributeIDs(selected);
			LineAttributeDef[] attrDefs = lineAttrDefs.getAll();
			if (attrIDs != null) {
				LineAttributeDef attrDef = null;
				ArrayList<LineAttributeDef> selectedAttrDefs = new ArrayList<LineAttributeDef>();
				for (int attrID : attrIDs) {
					attrDef = lineAttrDefs.getLineAttributeDef(attrID);
					if (attrDef != null) {
						selectedAttrDefs.add(attrDef);
					}
				}
				attrDefs = selectedAttrDefs.toArray(new LineAttributeDef[0]);
			}
			curAttrDefs = attrDefs;
			this.lineAttrListBox.setListData(attrDefs);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		LineAttributeDef selected = (LineAttributeDef) lineAttrListBox
				.getSelectedValue();
		attrTableModel.setAttributeDef(selected);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("ModuleAttribute" == cmd) {
			if (equationAttrDialog == null) {
				EquationAttributeTableModel tableModel = new EquationAttributeTableModel(
						equations, lineAttrDefs);
				JTable equationAttrTable = new JTable(tableModel);
				equationAttrTable.setRowHeight(20);

				// set table header
				equationAttrTable.getTableHeader().setPreferredSize(
						new Dimension(50, 40));
				DefaultTableCellHeaderRenderer headerRender = new DefaultTableCellHeaderRenderer();
				headerRender.setHorizontalAlignment(JLabel.CENTER);
				equationAttrTable.getTableHeader().setDefaultRenderer(
						headerRender);

				// set table cell render
				DefaultTableCellRenderer defCellRender = new DefaultTableCellRenderer();
				defCellRender.setHorizontalAlignment(SwingConstants.CENTER);
				equationAttrTable.setDefaultRenderer(String.class,
						defCellRender);
				CheckBoxTableCellRender chkRender = new CheckBoxTableCellRender();
				chkRender.setHorizontalAlignment(SwingConstants.CENTER);
				equationAttrTable.setDefaultRenderer(Boolean.class, chkRender);
				equationAttrDialog = new InfoDialog((Frame) null,
						new JScrollPane(equationAttrTable), TextResourceBundle
								.getInstance().getString(
										"Equation Attribute Relationships"),
						true);
				equationAttrDialog
						.setDefaultCloseOperation(InfoDialog.HIDE_ON_CLOSE);
				equationAttrDialog.setLocationRelativeTo(this);
				equationAttrDialog.setSize(ControlConstants.DIALOG_WIDTH,
						ControlConstants.DIALOG_HEIGHT);
			}

			equationAttrDialog.showDialog();
		} else if ("NewFilter".equals(cmd)) {
			lineAttrFilterPanel.setSelectedLineAttrDefs("",
					new LineAttributeDef[0]);
			lineAttrFilterDialog.showDialog();
			if (lineAttrFilterDialog.getOptionResult() == OKCancelDialog.DIALOG_CANCEL)
				return;
			String filterName = lineAttrFilterPanel.getFilterName();
			LineAttributeDef[] selectedAttrs = lineAttrFilterPanel
					.getSelectedLineAttrDef();
		} else if ("EditFilter".equals(cmd)) {
			String filerName = (String) comboxFilter.getSelectedItem();
			lineAttrFilterPanel.setSelectedLineAttrDefs(filerName, curAttrDefs);
			lineAttrFilterDialog.showDialog();
		} else if ("RemoveFilter".equals(cmd)) {
			pipeline.removeAttributeFilter((String) comboxFilter
					.getSelectedItem());
		}
	}

	public static void main(String[] args) {
		Pipeline pipeline = new Pipeline("PipelineDemo", 0, 1000);
		pipeline.addAttributeFilter("filter1", new int[] { 0, 1 });
		pipeline.addAttributeFilter("filter2", new int[] { 2, 3 });
		Section segment = new Section("s1", 0, 1000, -1);
		segment.addAttribute(new LineAttribute(0, 0, "a", 1));
		pipeline.addSegment(segment);
		LineAttributeDefCollection lineAttrDefs = new LineAttributeDefCollection();
		lineAttrDefs.addLineAttributeDef(new LineAttributeDef(0, "a"));
		lineAttrDefs.addLineAttributeDef(new LineAttributeDef(1, "b"));
		lineAttrDefs.addLineAttributeDef(new LineAttributeDef(2, "c"));
		lineAttrDefs.addLineAttributeDef(new LineAttributeDef(3, "d"));
		lineAttrDefs.addLineAttributeDef(new LineAttributeDef(4, "e"));

		EquationCollection equations = new EquationCollection();
		equations.addEquation(new Equation(0, "E1"));
		equations.addEquation(new Equation(1, "E2"));
		equations.addEquation(new Equation(2, "E3"));
		equations.addEquation(new Equation(3, "E4"));
		equations.addEquation(new Equation(4, "E5"));
		equations.addEquation(new Equation(5, "E6"));
		equations.addEquation(new Equation(6, "E7"));
		equations.addEquation(new Equation(7, "E8"));
		equations.addEquation(new Equation(8, "E9"));
		equations.addEquation(new Equation(9, "E10"));
		PipelineAttributePanel panel = new PipelineAttributePanel(pipeline,
				lineAttrDefs, equations);
		OKCancelDialog dialog = new OKCancelDialog((Frame) null, panel, "");
		dialog.setSize(800, 600);
		dialog.showDialog();
	}
}

/**
 * Pipeline Line Attribute Table Data Model
 * 
 * @author fujunwei
 * 
 */
class AttributeTableModel extends AbstractTableModel {
	private static final int DEFAULT_ROW_COUNT = 20;
	private static final int COLUMNCOUNT = 4;
	private static final String[] COLUMNS = {
			TextResourceBundle.getInstance().getString("Start Post"),
			TextResourceBundle.getInstance().getString("End Post") };
	public static final int COLUMN_INDEX_START = 0;
	public static final int COLUMN_INDEX_END = 1;
	public static final int COLUMN_INDEX_ATTRIBUTE = 2;
	public static final int COLUMN_INDEX_EDIT = 3;

	private Pipeline pipeline = null;
	private ArrayList<Section> segments; // segment collection
	private LineAttributeDef curAttrDef = null;
	private boolean isEditable = false;

	public AttributeTableModel(Pipeline pipeline) {
		this(pipeline, false);
	}

	public AttributeTableModel(Pipeline pipeline, boolean isEditable) {
		if (pipeline == null)
			throw new IllegalArgumentException("Pipeline should not be null");
		this.pipeline = pipeline;
		this.isEditable = isEditable;
		segments = new ArrayList<Section>();
		for (int i = 0; i < pipeline.getSegmentCount(); i++) {
			segments.add(pipeline.getSegmentAt(i).clone());
		}
	}

	public void save() {
		pipeline.setSegments(segments);
	}

	@Override
	public int getRowCount() {
		return (curAttrDef != null && segments != null && segments.size() > DEFAULT_ROW_COUNT) ? segments
				.size() : DEFAULT_ROW_COUNT;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLUMNCOUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (curAttrDef == null || segments == null || segments.size() == 0
				|| rowIndex >= segments.size())
			return null;
		Object value = null;
		Section segment = segments.get(rowIndex);
		if (columnIndex == COLUMN_INDEX_ATTRIBUTE) {
			Collection<LineAttribute> attrs = segment.getAttributes();
			if (attrs != null) {
				for (LineAttribute attr : attrs) {
					if (!curAttrDef.getName().equals(attr.getName()))
						continue;
					value = attr.getDistributeID();
				}
			}
		} else if (columnIndex == COLUMN_INDEX_START) {
			value = segment.getStart();
		} else if (columnIndex == COLUMN_INDEX_END) {
			value = segment.getEnd();
		}
		return value;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (value == null || rowIndex < 0 || curAttrDef == null)
			return;

		try {
			Section segment = null;
			if (rowIndex < segments.size()) {
				segment = segments.get(rowIndex);
			} else {
				segment = new Section(TextResourceBundle.getInstance()
						.getString("Segment") + String.valueOf(rowIndex + 1),
						0, 0, pipeline.getID());
				segment.addAttribute(new LineAttribute(curAttrDef.getId(),
						segment.getID(), curAttrDef.getName()));
				segments.add(segment);
				pipeline.addSegment(segment);
			}
			if (columnIndex == COLUMN_INDEX_ATTRIBUTE) {
				LineAttribute lineAttr = null;
				Collection<LineAttribute> attrs = segment.getAttributes();
				if (attrs != null) {
					for (LineAttribute attr : attrs) {
						if (curAttrDef.getName().equals(attr.getName())) {
							lineAttr = attr;
							break;
						}
					}
				}
				if (lineAttr == null) {
					lineAttr = new LineAttribute(curAttrDef.getId(),
							segment.getID(), curAttrDef.getName());
					segment.addAttribute(lineAttr);
				}
				lineAttr.setDistributeID(Integer.parseInt(value.toString()));
			} else if (columnIndex == COLUMN_INDEX_START) {
				segment.setStart(Float.parseFloat(value.toString()));
			} else if (columnIndex == COLUMN_INDEX_END) {
				segment.setEnd(Float.parseFloat(value.toString()));
			}
		} catch (NumberFormatException ex) {

		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == COLUMN_INDEX_EDIT) {
			return "";
		} else if (column == COLUMN_INDEX_ATTRIBUTE) {
			return curAttrDef != null ? curAttrDef.getName() : "";
		} else {
			return COLUMNS[column];
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (curAttrDef == null) ? false : isEditable;
	}

	// set selected line attribute
	public void setAttributeDef(LineAttributeDef attrDef) {
		this.curAttrDef = attrDef;
		segments.clear();
		Section[] matchedSegments = pipeline.getSegmentByAttr(attrDef);
		if (matchedSegments != null && matchedSegments.length != 0) {
			for (Section segment : matchedSegments) {
				segments.add(segment);
			}
		}
		this.fireTableStructureChanged();
	}

	// get selected line attribute
	public LineAttributeDef getAttributeDef() {
		return this.curAttrDef;
	}

}

class EquationAttributeTableModel extends AbstractTableModel {
	private EquationCollection equations;
	private LineAttributeDefCollection lineAttrs;

	public EquationAttributeTableModel(EquationCollection equations,
			LineAttributeDefCollection lineAttrs) {
		this.equations = equations;
		this.lineAttrs = lineAttrs;
	}

	@Override
	public int getRowCount() {
		return lineAttrs == null ? 0 : lineAttrs.getCount();
	}

	@Override
	public int getColumnCount() {
		return equations == null ? 0 : equations.getCount() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (equations == null || lineAttrs == null)
			return columnIndex == 0 ? "" : false;

		if (columnIndex == 0) {
			return lineAttrs.getLineAttributeDefAt(rowIndex).getName();
		} else {
			return true;
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return TextResourceBundle.getInstance().getString("Line Attribute");
		} else {
			Equation equation = equations.getEquationAt(column - 1);
			return equation.getName();
		}
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return column == 0 ? String.class : Boolean.class;
	}

}

class AttributeTableCellEditor extends AbstractCellEditor implements
		TableCellEditor, ActionListener, ItemListener {
	private AttributeTableModel tableModel;
	private Component editingComp;

	public AttributeTableCellEditor(AttributeTableModel tableModel) {
		this.tableModel = tableModel;
	}

	@Override
	public Object getCellEditorValue() {
		if (editingComp instanceof JTextField) {
			JTextField txtField = (JTextField) editingComp;
			return txtField.getText();
		} else if (editingComp instanceof JComboBox) {
			JComboBox comboBox = (JComboBox) editingComp;
			return comboBox.getSelectedItem();
		}
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Object editingValue = table.getValueAt(row, column);
		Component editingComp = null;
		if (column == AttributeTableModel.COLUMN_INDEX_ATTRIBUTE) {
			// if the column is the last column
			LineAttributeDef attrDef = tableModel.getAttributeDef();
			if (attrDef.isSelectable()) {
				JComboBox<Object> comboBox = new JComboBox<Object>(
						attrDef.getOptionalDistributes());
				comboBox.addItemListener(this);
				editingComp = comboBox;
			} else {
				JTextField txtField = new JTextField(
						editingValue != null ? editingValue.toString() : "");
				txtField.addActionListener(this);
				editingComp = txtField;
			}
		} else if (column == AttributeTableModel.COLUMN_INDEX_EDIT) {
			JButton btnEdit = new JButton("icons/distribution_edit");
			btnEdit.setActionCommand("EditDistribution");
			btnEdit.addActionListener(this);
			editingComp = btnEdit;
		} else {
			JFormattedTextField txtField = new JFormattedTextField(
					editingValue != null ? editingValue.toString() : "0.0");
			txtField.setActionCommand("EditTextField");
			txtField.addActionListener(this);
			editingComp = txtField;
		}

		this.editingComp = editingComp;
		return editingComp;
	}

	/**
	 * When an action is performed, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("EditDistribution".equals(cmd)) {
			JOptionPane.showMessageDialog(null, "编辑参数分布函数");
		}
		stopCellEditing();
	}

	/**
	 * When an item's state changes, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public void itemStateChanged(ItemEvent e) {
		stopCellEditing();
	}

}
