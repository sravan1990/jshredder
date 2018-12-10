import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.screen.FieldScreenFactory
import com.atlassian.jira.issue.fields.screen.FieldScreenManager
import com.atlassian.jira.issue.fields.screen.FieldScreenSchemeManager
import com.atlassian.jira.web.action.admin.issuefields.screens.ViewFieldScreens
import com.atlassian.jira.workflow.WorkflowManager

FieldScreenManager fieldScreenManager = ComponentAccessor.getFieldScreenManager()
FieldScreenFactory fieldScreenFactory = ComponentAccessor.getComponent(FieldScreenFactory.class)
FieldScreenSchemeManager fieldScreenSchemeManager = ComponentAccessor.getComponent(FieldScreenSchemeManager.class)
WorkflowManager workflowManager = ComponentAccessor.getWorkflowManager()
ViewFieldScreens viewFieldScreens = new ViewFieldScreens(fieldScreenManager, fieldScreenFactory, fieldScreenSchemeManager, workflowManager)

log.error 'viewFieldScreens: ' + viewFieldScreens

// use StringBuffer to spit out log to screen for ScriptRunner Console
def sb = new StringBuffer()

for(def fieldScreen : fieldScreenManager.getFieldScreens()) {    
    if(viewFieldScreens.isDeletable(fieldScreen)) {
        sb.append("Deleted ${fieldScreen.getName()}")
        fieldScreenManager.removeFieldScreen(fieldScreen.getId())
        sb.append(" xxxxx ")
    }

    /*
    // get screen schemes that use the screen
    def fieldScreenSchemes = viewFieldScreens.getFieldScreenSchemes(fieldScreen)
    int screenSize = fieldScreenSchemes.size()
    sb.append("${fieldScreenSchemes.size()}")
    sb.append(" xxxxx ")

    // get the workflows that use the screen, if any
    def workflows = viewFieldScreens.getWorkflows(fieldScreen)
    def workflowSize = workflows.size()
    sb.append("${workflows.size()}")
    */
}

return sb.toString()