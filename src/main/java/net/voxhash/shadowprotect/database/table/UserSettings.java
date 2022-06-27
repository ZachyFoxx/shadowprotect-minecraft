package net.voxhash.shadowprotect.database.table;

import java.util.EnumSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name = "UserSettings")
public class UserSettings {

    public enum SettingsFlag {
        // Do not reorder.
        SETTING_SIGNSPY(0),
        SETTING_STAFFCHAT(1);

        // Value of *this* flag
        public long flag;

        // Constructor
        SettingsFlag(long id) {
            this.flag = 1 << id;
        }

        // Get the flag value as a bit shift
        public long value() {
            return this.flag;
        }

        // Get a list of flags
        public static EnumSet<SettingsFlag> of(long value) {
            EnumSet<SettingsFlag> flags = EnumSet.noneOf(SettingsFlag.class);

            for (SettingsFlag f : SettingsFlag.values())
            {
                if ((value & f.value()) == f.value())
                    flags.add(f);
            }
            return flags;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "flags")
    private long flags;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="blockeduser_id")
    private Set<BlockedUser> blockedusers;

    public UserSettings(long flags) {
        this.flags = flags;
    }

    public EnumSet<SettingsFlag> getSettings() {
        return SettingsFlag.of(this.flags);
    }

    public void setSetting(SettingsFlag f) {
        this.flags |= f.flag;
    }
}