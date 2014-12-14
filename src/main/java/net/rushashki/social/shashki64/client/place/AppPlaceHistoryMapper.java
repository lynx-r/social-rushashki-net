package net.rushashki.social.shashki64.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:50
 */
@WithTokenizers({HomePlace.Tokenizer.class, PlayLentaPlace.Tokenizer.class, PlayPlace.Tokenizer.class,
    SignInPlace.Tokenizer.class, ProfilePlace.Tokenizer.class, SettingsPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
