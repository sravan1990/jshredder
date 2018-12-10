import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.screen.FieldScreenManager
import com.atlassian.jira.issue.fields.screen.FieldScreenSchemeManager
import com.atlassian.jira.workflow.WorkflowActionsBean

def fieldScreenManager = ComponentAccessor.getComponent(FieldScreenManager)
def fieldScreenSchemeManager = ComponentAccessor.getComponent(FieldScreenSchemeManager)

def allScreensIds = fieldScreenManager.getFieldScreens().collect {it.id}
def screenSchemes = fieldScreenSchemeManager.fieldScreenSchemes

def screenIdsWithScheme = []
screenSchemes.each {
    def items = fieldScreenSchemeManager.getFieldScreenSchemeItems(it).collect {it.fieldScreen.id}
    screenIdsWithScheme << items
}

def workflowBean = new WorkflowActionsBean()
def screenIdsWithWorkflowAction = []

ComponentAccessor.workflowManager.workflows?.each {
    screenIdsWithWorkflowAction << it.allActions?.findResults { workflowBean.getFieldScreenForView(it)?.id ?: null }
}

def screenIdsInWorkflows = screenIdsWithWorkflowAction.flatten().unique()
def screensNotAssociatedToScheme = (allScreensIds - screenIdsWithScheme.flatten() - screenIdsInWorkflows)

screensNotAssociatedToScheme?.each {
    log.debug "${fieldScreenManager.getFieldScreen(it).name} is not associated with any Screen Scheme or workflow and will be removed"
    fieldScreenManager.removeFieldScreen(it)
}