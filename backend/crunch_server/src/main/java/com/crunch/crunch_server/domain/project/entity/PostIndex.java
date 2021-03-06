package com.crunch.crunch_server.domain.project.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "postindex")
public class PostIndex {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id", insertable = false, updatable = false)
    @EmbeddedId
    private PostIndexIdentity postIndexIdentity;

    private String title;

    private int fee;
}
