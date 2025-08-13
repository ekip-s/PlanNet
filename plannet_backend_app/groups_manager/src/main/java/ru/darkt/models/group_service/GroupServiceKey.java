package ru.darkt.models.group_service;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GroupServiceKey implements Serializable {

    @Column(name = "group_id")
    private UUID groupId;
    @Column(name = "service_id")
    private UUID serviceId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GroupServiceKey that = (GroupServiceKey) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, serviceId);
    }
}
