package ru.darkt.models.group_service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.darkt.models.group.Group;
import ru.darkt.models.service.Service;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_services")
public class GroupServiceModel {

    @EmbeddedId
    private GroupServiceKey id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private Service service;

    public GroupServiceModel(UUID groupId, UUID serviceId) {
        this.id = new GroupServiceKey(groupId, serviceId);
        this.createdAt = LocalDateTime.now();
        this.group = new Group(groupId);
        this.service = new Service(serviceId);
    }
}