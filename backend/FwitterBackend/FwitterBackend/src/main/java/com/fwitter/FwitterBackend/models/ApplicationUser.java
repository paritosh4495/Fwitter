package com.fwitter.FwitterBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;


    private String bio;

    private String nickname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture", referencedColumnName = "image_id")
    private Image profilePicture;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banner_picture", referencedColumnName = "image_id")
    private Image bannerPicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "following",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "following_id")
            }
    )
    @JsonIgnore
    private Set<ApplicationUser> following;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "followers",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "followers_id")
            }
    )
    @JsonIgnore
    private Set<ApplicationUser> followers;





    /* Security */



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    private boolean enabled;

    @Column(nullable = true)
    @JsonIgnore
    private Long verification;


    public ApplicationUser(){
        this.authorities = new HashSet<>();
        this.enabled = false;
        this.following = new HashSet<>();
        this.followers = new HashSet<>();
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "profilePicture=" + profilePicture +
                ", bannerPicture=" + bannerPicture +
                ", following=" + following.size() +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", nickname='" + nickname + '\'' +
                ", followers=" + followers.size() +
                ", authorities=" + authorities +
                ", enabled=" + enabled +
                ", verification=" + verification +
                '}';
    }
}
