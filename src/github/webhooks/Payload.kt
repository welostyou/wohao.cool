package github.webhooks

import github.Issue
import github.Label

data class IssuePayload(val action: IssueAction, val issue: Issue?)

data class LabelPayload(val action: LabelAction, val label: Label)
