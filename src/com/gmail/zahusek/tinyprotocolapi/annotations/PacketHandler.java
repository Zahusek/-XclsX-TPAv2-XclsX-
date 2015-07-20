package com.gmail.zahusek.tinyprotocolapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gmail.zahusek.tinyprotocolapi.enums.Packet;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {

	Packet packet();

}
