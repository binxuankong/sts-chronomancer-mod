package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class FlashbulbAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard.CardType typeToCheck;

    public FlashbulbAction(AbstractCard.CardType type) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.typeToCheck = type;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.discardPile.isEmpty() || this.p.hand.size() >= 10) {
                this.isDone = true;
                return;
            }

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var1 = this.p.discardPile.group.iterator();

            AbstractCard card;
            while (var1.hasNext()) {
                card = (AbstractCard) var1.next();
                if (card.type == this.typeToCheck) {
                    tmp.addToRandomSpot(card);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            if (!tmp.isEmpty()) {
                tmp.shuffle();
                card = tmp.getBottomCard();
                tmp.removeCard(card);
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0F);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.current_x = CardGroup.DISCARD_PILE_X;
                card.current_y = CardGroup.DISCARD_PILE_Y;
                card.setCostForTurn(0);
                this.p.discardPile.removeCard(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}
