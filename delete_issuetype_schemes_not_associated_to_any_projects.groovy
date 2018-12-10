import com.atlassian.jira.component.ComponentAccessor

def schemeManager = ComponentAccessor.issueTypeScreenSchemeManager

def sb = new StringBuffer()

sb.append("Deleted issue type screen schemes with no associated projects:<br/><br/>\n")
schemeManager.issueTypeScreenSchemes.each {
 try{
   if(it.projects.size() == 0) {
     sb.append("${it.name}<br/>\n")
     schemeManager.removeIssueTypeScreenScheme(it);
   }
 }
 catch(Exception e) {
   //noop
   sb.append("Error: " + e + "<br/>\n");
 }
}

return sb.toString()