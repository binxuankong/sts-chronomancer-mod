package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard c;

    public DiscardCardAction(AbstractCard card) {
        this.p = AbstractDungeon.player;
        this.c = card;
    }

    public void update() {
        p.hand.moveToDiscardPile(c);
        c.triggerOnManualDiscard();
        this.isDone = true;
    }
}