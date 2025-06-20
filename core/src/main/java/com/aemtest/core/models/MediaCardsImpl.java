package com.aemtest.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = MediaCards.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MediaCardsImpl implements MediaCards{

    private static Logger log = LoggerFactory.getLogger(MediaCardsImpl.class);

    @SlingObject
    Resource currentResource;

    @ValueMapValue
    public String customClass;

    @ValueMapValue
    public String heading;

    @Override
    public String getCustomClass() {
        return customClass;
    }

    @Override
    public String getHeading() {
        return heading;
    }

    @Override
    public List<Map<String, String>> getMediaCards() {

        List<Map<String, String>> mediacardlist = new ArrayList<>();
        try {
            Resource resource = currentResource.getChild("mediaCards");
            if (resource != null) {
                for (Resource cards : resource.getChildren()) {
                    Map<String, String> card = new HashMap<>();
                    card.put("videosrc", cards.getValueMap().get("videosrc", String.class));
                    card.put("cardImage", cards.getValueMap().get("cardImgFileReference", String.class));
                    card.put("cardTitle", cards.getValueMap().get("cardTitle", String.class));
                    card.put("cardDescription", cards.getValueMap().get("cardDescription", String.class));
                    card.put("ctalabel", cards.getValueMap().get("ctalabel", String.class));
                    card.put("ctalink", cards.getValueMap().get("ctalink", String.class));
                    card.put("newTab", cards.getValueMap().get("newTab", String.class));
                    mediacardlist.add(card);
                }
            }
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
        }
        return mediacardlist;
    }
}
