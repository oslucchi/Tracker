package applications;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class NodesManager {

	public static ArrayList<Node> GetNodeList(Node node, String nodeName)
	{
		ArrayList<Node> nodeList = new ArrayList<Node>();
		if (node.hasChildNodes())
		{
			NodeList groups = node.getChildNodes();
			for(int i = 0; i < groups.getLength(); i ++)
			{
				String s = groups.item(i).getNodeName();
				if ((s != null) && (s.compareTo(nodeName) == 0))
				{
					nodeList.add(groups.item(i));
				}
			}
		}
		return(nodeList);
	}
}
