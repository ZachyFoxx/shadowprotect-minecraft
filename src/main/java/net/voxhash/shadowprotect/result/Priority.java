package net.voxhash.shadowprotect.result;

/**
 * An enum of priorities messages can be sent with.
 */
public enum Priority {
    /**
     * Default priority of sent messages.
     */

    DEFAULT,
    /**
     * Reserved for messages with low priority, such as adverts/reminders etc.
     */
    LOW,

    /**
     * Reserved for messages with high priority, such as staff announcements, warnings etc.
     */
    IMPORTANT,

    /**
     * Reserved for messages sent directly via DMs
     */
    DIRECT,

    /**
     * Reserved for system messages sent via the server or plugins implementing the chat API.
     */
    SYSTEM;

    /**
     * Returns true if this is greater than the target priority.
     *
     * @param target The target priority
     * @return {@link Boolean}
     */
    public Boolean isGreaterThan(Priority target) {
        return this.ordinal() > target.ordinal();
    }

    /**
     * Returns true if this is greater than or equal to the target priority.
     *
     * @param target The target priority
     * @return {@link Boolean}
     */
    public Boolean isGreaterThanOrEqualTo(Priority target) {
        return this.ordinal() >= target.ordinal();
    }
}