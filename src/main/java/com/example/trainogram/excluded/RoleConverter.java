//package com.example.trainogram.model;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//
//public class RoleConverter {
//    @Converter(autoApply = true)
//    public static class FieldConverter implements AttributeConverter<Role,Integer> {
//
//        @Override
//        public Integer convertToDatabaseColumn( Role role ) {
//            return role.getId();
//        }
//
//        @Override
//        public Role convertToEntityAttribute( Integer id ) {
//            return Role.fromId(id);
//        }
//    }
//}
