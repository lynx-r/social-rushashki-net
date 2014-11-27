package net.rushashki.shashki64.client.cell.renderer;

import net.rushashki.shashki64.shared.model.dto.PlayDto;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 20:47
 */
public class PlayCellDtoRenderer {

  private PlayDto playDto;

  public PlayCellDtoRenderer(PlayDto playDto) {
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

  public String getDeskHtml() {
    return playDto.getDeskHtml();
  }

}
