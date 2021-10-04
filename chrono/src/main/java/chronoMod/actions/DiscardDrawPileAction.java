package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p;

    public DiscardDrawPileAction(AbstractPlayer player, int numCards) {
        this.p = player;
        this.amount = numCards;
    }

    public void update() {
        for (int i = 0; i < this.amount; i++) {
            if (p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            } else {
                AbstractCard card = p.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.moveToDiscardPile(card);
            }
        }

        this.isDone = true;
    }
}
