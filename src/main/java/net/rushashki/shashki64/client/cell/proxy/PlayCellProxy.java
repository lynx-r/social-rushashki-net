package net.rushashki.shashki64.client.cell.proxy;

import com.google.gwt.safehtml.shared.SafeHtml;
import net.rushashki.shashki64.shared.model.dto.PlayDto;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 20:47
 */
public class PlayCellProxy {

  private PlayDto playDto;

  public PlayCellProxy() {}

  public PlayCellProxy(PlayDto playDto) {
    this.playDto = playDto;
  }

  public String getTitle() {
    return playDto.getTitle();
  }

  public String getWhitePlayer() {
    return playDto.getWhitePlayer();
  }

  public String getBlackPlayer() {
    return playDto.getBlackPlayer();
  }

  public SafeHtml getDeskHtml() {
    return playDto.getDeskHtml();
  }

  public PlayDto getDto() {
    return playDto;
  }

  public void setDto(PlayDto playDto) {
    this.playDto = playDto;
  }

  public void updateCanvas() {
    playDto.getUpdateCanvas().update();
  }

}
