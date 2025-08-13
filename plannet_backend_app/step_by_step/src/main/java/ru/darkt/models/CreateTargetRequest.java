package ru.darkt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTargetRequest {

    private UUID id;
    private UUID parentId;
    private TargetType type;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "CreateTargetRequest{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
