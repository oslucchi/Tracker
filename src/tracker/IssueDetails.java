package tracker;

import java.util.ArrayList;

public class IssueDetails {
	public class Mail {
		String mailTo;
		String displayName;
		public Mail(String mailTo, String displayName)
		{
			setMailTo(mailTo);
			setDisplayName(displayName);
		}
		public String getMailTo() {
			return mailTo;
		}
		public void setMailTo(String mailTo)
		{
			this.mailTo = mailTo;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName)
		{
			this.displayName = displayName;
		}
	}

	public class AdditionalInfo {
		String name;
		String showIfOneOf;
		int fieldLength;
		boolean clearOnHide;
		public AdditionalInfo(String name, String showCondition, int fieldLength, boolean clearOnHide)
		{
			this.name = name;
			this.showIfOneOf = showCondition;
			this.fieldLength = fieldLength;
			this.clearOnHide = clearOnHide;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getShowIfOneOf() {
			return showIfOneOf;
		}
		public void setShowIfOneOf(String showIfOneOf) {
			this.showIfOneOf = showIfOneOf;
		}
		public int getFieldLength() {
			return fieldLength;
		}
		public void setFieldLength(int fieldLength) {
			this.fieldLength = fieldLength;
		}
		public boolean isClearOnHide() {
			return clearOnHide;
		}
		public void setClearOnHide(boolean clearOnHide) {
			this.clearOnHide = clearOnHide;
		}
	}
	
	public class IssueDetailsItem {
		private String name;
		private String id;
		private ArrayList<Mail> mailTo = new ArrayList<Mail>();
		
		public void setName(String name)
		{
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void AddMail(Mail item)
		{
			mailTo.add(item);
		}
		public ArrayList<Mail> getMailTo() {
			return mailTo;
		}
	}

	public String name;
	public String groupName;
	public String controlType;
	public ArrayList<IssueDetailsItem> items;
	private AdditionalInfo infoText;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public ArrayList<IssueDetailsItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<IssueDetailsItem> items) {
		this.items = items;
	}
	public int getItemsSize()
	{
		return(items.size());
	}
	public AdditionalInfo getInfoText() {
		return infoText;
	}
	public void setInfoText(AdditionalInfo infoText) {
		this.infoText = infoText;
	}
}
