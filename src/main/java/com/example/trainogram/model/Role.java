package com.example.trainogram.model;
//    USER(Set.of(Permission.READ)),
//    ADMIN(Set.of(Permission.READ, Permission.WRITE));


//    private final Set<Permission> permissions;

//    Role(Set<Permission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<Permission> getPermissions(){return permissions;}
//
//    public Set<SimpleGrantedAuthority> getAuthorities(){
//        return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//    }


//    private String authority;

//    Role(String authority) {
//        this.authority=authority;
//    }


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ToString
public enum Role /*implements GrantedAuthority*/ {


    USER,
    ADMIN

  /*  @Override
    public String getAuthority() {
        return this.name();
    }*/

/*
    ADMIN("ADMIN", 0),
    USER("USER", 1);
    @Setter
    @Getter
    @Accessors(fluent = true)
    private Integer id;

    @Setter
    @Accessors(fluent = true)
    private String authority;

    @JsonCreator
    public static Role fromId(Integer id){
        for (Role o: Role.values()){
            if ( o.id.compareTo(id) == 0 ){
                return o;
            }
        }
        return null;
    }

    Role(String authority, int id) {
        this.id = id;
        this.authority = authority;
    }

    @JsonValue
    @Override
    public String getAuthority() {
        return authority;
    }

    public Integer getId() {
        return id;
    }*/


}

