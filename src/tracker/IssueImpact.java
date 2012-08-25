package tracker;

import java.util.ArrayList;

public class IssueImpact {
	public class Selection
	{
		boolean defaultOpt;
		String name;
		String groupName;
		String value;
		double	severity;
		double	priority;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean getDefaultOpt() {
			return defaultOpt;
		}
		public void setDefaultOpt(boolean defaultOpt) {
			this.defaultOpt = defaultOpt;
		}
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public int getSeverity() {
			return new Double(severity).intValue();
		}
		public void setSeverity(int severity) {
			this.severity = new Integer(severity).doubleValue();
		}
		public int getPriority() {
			return new Double(priority).intValue();
		}
		public void setPriority(int priority) {
			this.priority = new Integer(priority).doubleValue();;
		}
		public double getSeverityWA() {
			return severity;
		}
		public void setSeverityWA(double severity) {
			this.severity = severity;
		}
		public double getPriorityWA() {
			return priority;
		}
		public void setPriorityWA(double priority) {
			this.priority = priority;
		}
	}
	
	public class IssueItem
	{
		private String name;
		private String subDesc;
		private ArrayList<Selection> selectionList;
		public int getSelectionListSize()
		{
			return(selectionList.size());
		}
		public String getName()
		{
			return(name);
		}
		public ArrayList<Selection> getSelectionList() {
			return selectionList;
		}
		public void setSelectionList(ArrayList<Selection> selectionList) {
			this.selectionList = selectionList;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSubDesc() {
			return subDesc;
		}
		public void setSubDesc(String subDesc) {
			this.subDesc = subDesc;
		}
	}
	public String impactName;
	public ArrayList<IssueItem> items;
}
