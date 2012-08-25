function SetTextInfoVisibiliy(currentTag, showIfOneOf, textInfoTag, clearOnHide)
{
  var tagList = showIfOneOf.split("|");
  var i = 0;
  for(i = 0; i < tagList.length; i++)
  {
	  if (tagList[i] == currentTag)
		  break;
  }
  var textInfoTagId = document.getElementById(textInfoTag);
  textInfoTagId.style.visibility = "visible";
  if ((showIfOneOf != "") && (i >= tagList.length))
  {
	  textInfoTagId.style.visibility = "hidden";
	  if (clearOnHide)
	  {
		  textInfoTagId.value = "";
	  }
  }
  return false;
}

function SeverityColor(tagName, chkValue)
{
  var setting = new Array(4);
  setting[0] = [0,"white","#00D700","LOW"];
  setting[1] = [0,"black","#C6F015","MED"];
  setting[2] = [0,"black","#F07115","HIGH"];
  setting[3] = [100,"white","#D70000","CRITICAL"];

  setting[0][0] = document.getElementById(tagName + 'Low').value;
  setting[1][0] = document.getElementById(tagName + 'Med').value;
  setting[2][0] = document.getElementById(tagName + 'Hig').value;

  tag = document.getElementById(tagName);
  for(i = 0; i < setting.length; i++)
  {
    if (parseInt(chkValue) <= parseInt(setting[i][0]))
    {
      tag.style.color = setting[i][1];
      tag.style.backgroundColor = setting[i][2];
      tag.value = setting[i][3];
      break;
    }
  }
}

function CantChange()
{
  var priority = 0;
  var severity = 0;
  priority = document.getElementById('priorityVal').value;
  severity = document.getElementById('severityVal').value;
  SeverityColor('severity', severity);
  SeverityColor('priority', priority);
}

function CalculatePrio(clickOnImpact)
{
	var prioValueList = document.getElementById("prioValueList"); 
	var arrayPrio = prioValueList.value.split("|");
	var div = document.getElementById("Impact");
	var elms = div.getElementsByTagName("*");
	var priority = 0;
	var severity = 0;
	for(var i = 0, maxI = elms.length; i < maxI; ++i)
	{
		var elm = elms[i];
		if (elm.checked)
		{
			for(var y = 0; y < arrayPrio.length; y++)
			{
				var prioItem = arrayPrio[y].split(";");
				if (elm.value == prioItem[0])
				{
					priority += parseInt(prioItem[2]);
					severity += parseInt(prioItem[1]);
					break;
				}
			}
		}
	}
	var tag;
	if ((clickOnImpact == "wka-yes") || (clickOnImpact == "wka-no"))
	{
		// Using mitigators from workaround if available
		for(var i = 0, maxI = elms.length; i < maxI; ++i)
		{
			var elm = elms[i];
			if (elm.value == clickOnImpact)
			{
				for(var y = 0; y < arrayPrio.length; y++)
				{
					var prioItem = arrayPrio[y].split(";");
					if (elm.value == prioItem[0])
					{
						priority *= parseFloat(prioItem[2]);
						severity *= parseFloat(prioItem[1]);
						break;
					}
				}
			}
		}
	}
	// Setting priority and severity
	tag = document.getElementById("priorityVal");
	tag.value = priority;
	tag = document.getElementById("severityVal");
	tag.value = severity;
	SeverityColor("priority", priority);
	SeverityColor("severity", severity);
}
