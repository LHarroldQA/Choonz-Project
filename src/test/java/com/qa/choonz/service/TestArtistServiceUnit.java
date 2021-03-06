package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

@SpringBootTest
class TestArtistServiceUnit {

	@Autowired
	private ArtistService service;
	
	@MockBean
	private ArtistRepository repo;
	
	@MockBean
	private ModelMapper modelMapper;
	//--[ Test Variables ]--
		
		private ArtistDTO artistDTO;
		private Artist testArtist;
		private Artist testArtistWithId;
		final Long id = 1l;
		final String name = "Pink Floyd";
		final String picture = "TestPicture";
		private List<Album> testAlbums;
		private List<Artist> artistList;

		//--[ Test Setup ]--
		@BeforeEach
		void init() {
			// Initialize testing vars
			this.testAlbums = new ArrayList<Album>();
			this.artistList = new ArrayList<>();
			this.testArtist = new Artist(
					this.id, 
					this.name, 
					this.picture,
					this.testAlbums);
			artistList.add(testArtist);
		}
		
		@Test
		void testCreateArtist() {
			when(this.repo.save(this.testArtist)).thenReturn(this.testArtistWithId);
			
			when(this.modelMapper.map(this.testArtistWithId, ArtistDTO.class)).thenReturn(this.artistDTO);
			
			ArtistDTO expected = this.artistDTO;
			ArtistDTO actual = this.service.create(this.testArtist);
			assertThat(expected).isEqualTo(actual);
			
			verify(this.repo, times(1)).save(this.testArtist);
		}
		
		@Test
		void testReadArtist() {
			when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testArtist));
			
			when(this.modelMapper.map(testArtistWithId, ArtistDTO.class)).thenReturn(artistDTO);
			
			assertThat(this.artistDTO).isEqualTo(this.service.read(this.id));
			verify(this.repo, times(1)).findById(this.id);
		}
		
		@Test
		void testReadAllArtists() {
			when(this.repo.findAll()).thenReturn(this.artistList);
			
			when(this.modelMapper.map(this.testArtistWithId, ArtistDTO.class)).thenReturn(artistDTO);
			
			assertThat(this.service.read().isEmpty()).isFalse();
			verify(this.repo, times(1)).findAll();
		}
		
		@Test
		void testUpdateArtist() {
			Artist artist = new Artist(this.id, this.name, this.picture, this.testAlbums);
//			artist.setId(this.id);
			
			ArtistDTO artistDTO = new ArtistDTO(this.id, this.name, this.picture, this.testAlbums);
			
			Artist updatedArtist = new Artist(this.id, artistDTO.getName(), artistDTO.getPicture(), this.testAlbums);
			
			ArtistDTO updatedArtistDTO = new ArtistDTO(this.id, updatedArtist.getName(), updatedArtist.getPicture(), updatedArtist.getAlbums());
			
			
			when(this.repo.findById(this.id)).thenReturn(Optional.of(artist));
			
			when(this.repo.save(artist)).thenReturn(updatedArtist);
			
			when(this.modelMapper.map(updatedArtist, ArtistDTO.class)).thenReturn(updatedArtistDTO);
			
			assertThat(updatedArtistDTO).isEqualTo(this.service.update(testArtist, this.id));
			
			verify(this.repo, times(1)).findById(1L);
			verify(this.repo, times(1)).save(updatedArtist);
		}
		
		@Test
		void testDeleteArtist() {
			when(this.repo.existsById(id)).thenReturn(true, false);
			
			assertThat(this.service.delete(id)).isFalse();
			verify(this.repo, times(1)).deleteById(id);
			verify(this.repo, times(1)).existsById(id);
		}
}
