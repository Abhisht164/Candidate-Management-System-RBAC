package com.learning.candidatemanagementsystem.model;

import com.learning.candidatemanagementsystem.model.keys.UserRoleId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter@Setter
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    private LocalDateTime assignedAt = LocalDateTime.now();


    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

