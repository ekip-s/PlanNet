package ru.darkt.models.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.darkt.models.group_user.GroupUserResponse;
import ru.darkt.models.service.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    private UUID id;
    private LocalDateTime createdAt;
    private String name;
    List<GroupUserResponse> groupUsers;
    List<Service> services;
}
