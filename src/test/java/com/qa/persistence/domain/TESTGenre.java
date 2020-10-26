package com.qa.persistence.domain;

//--[ Imports ]--
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;

//===[ Testing Code ]===
public class TESTGenre {
	//--[ Test Variables ]--
	Genre testGenre;
	final Long id = 1l;
	final String name = "Psychadelic Rock";
	final String description = "Unusual rock-music.";
	List<Album> albums;
	
	//--[ Test Setup ]--
	@BeforeEach
	void init() {
		this.albums = new ArrayList<Album>();
		this.testGenre = new Genre(
				this.id, 
				this.name, 
				this.description, 
				this.albums);
	}
}
