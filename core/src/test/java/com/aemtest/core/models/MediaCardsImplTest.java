package com.aemtest.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaCardsImplTest {

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private Resource currentResource;

    @Mock
    private Resource mediaCardResource;

    @Mock
    private Resource card1;

    @Mock
    private ValueMap valueMap1;

    @InjectMocks
    private MediaCardsImpl mediaCardsImpl;

    @BeforeEach
    void setUp() {
        mediaCardsImpl.customClass = "custom-class";
        mediaCardsImpl.heading = "Media Cards Heading";
        mediaCardsImpl.currentResource = currentResource;
    }

    @Test
    void testGetCustomClass() {
        assertEquals("custom-class", mediaCardsImpl.getCustomClass());
    }

    @Test
    void testGetHeading() {
        assertEquals("Media Cards Heading", mediaCardsImpl.getHeading());
    }

    @Test
    void testGetMediaCards() {
        when(currentResource.getChild("mediaCards")).thenReturn(mediaCardResource);
        when(mediaCardResource.getChildren()).thenReturn(Collections.singleton(card1));
        when(card1.getValueMap()).thenReturn(valueMap1);
        when(valueMap1.get("videosrc", String.class)).thenReturn("video.mp4");
        when(valueMap1.get("cardImgFileReference", String.class)).thenReturn("/content/dam/image.png");
        when(valueMap1.get("cardTitle", String.class)).thenReturn("Card Title");
        when(valueMap1.get("cardDescription", String.class)).thenReturn("Description");
        when(valueMap1.get("ctalabel", String.class)).thenReturn("Read More");
        when(valueMap1.get("ctalink", String.class)).thenReturn("/link");
        when(valueMap1.get("newTab", String.class)).thenReturn("true");
        List<Map<String, String>> mediaCards = mediaCardsImpl.getMediaCards();

        assertNotNull(mediaCards);
        assertEquals(1, mediaCards.size());
        Map<String, String> card = mediaCards.get(0);
        assertEquals("video.mp4", card.get("videosrc"));
        assertEquals("/content/dam/image.png", card.get("cardImage"));
        assertEquals("Card Title", card.get("cardTitle"));
        assertEquals("Description", card.get("cardDescription"));
        assertEquals("Read More", card.get("ctalabel"));
        assertEquals("/link", card.get("ctalink"));
        assertEquals("true", card.get("newTab"));
    }

    @Test
    void testGetMediaCards_Empty() {
        when(currentResource.getChild("mediaCards")).thenReturn(null);
        List<Map<String, String>> mediaCards = mediaCardsImpl.getMediaCards();
        assertNotNull(mediaCards);
        assertTrue(mediaCards.isEmpty());
    }

    @Test
    void testGetMediaCards_ExceptionFromGetChildren() {
        when(currentResource.getChild("mediaCards")).thenReturn(mediaCardResource);
        when(mediaCardResource.getChildren()).thenThrow(new RuntimeException("Error in getChildren"));
        List<Map<String, String>> mediaCards = mediaCardsImpl.getMediaCards();
        assertNotNull(mediaCards);
        assertTrue(mediaCards.isEmpty());
    }
}
