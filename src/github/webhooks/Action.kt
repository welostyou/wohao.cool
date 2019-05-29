package github.webhooks

enum class LabelAction { DELETED, EDITED, CREATED }

enum class IssueAction {
    CREATED,
    OPENED,
    EDITED,
    DELETED,
    TRANSFERRED,
    PINNED,
    UNPINNED,
    CLOSED,
    REOPENED,
    ASSIGNED,
    UNASSIGNED,
    LABELED,
    UNLABELED,
    LOCKED,
    UNLOCKED,
    MILESTONED,
    DEMILESTONED
}