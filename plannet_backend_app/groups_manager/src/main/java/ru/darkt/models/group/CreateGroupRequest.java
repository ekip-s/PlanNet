package ru.darkt.models.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {

    private UUID id;
    private String name;
    private UUID[] services;


    @Override
    public String toString() {
        return "CreateGroupRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", services=" + Arrays.toString(services) +
                '}';
    }
}
