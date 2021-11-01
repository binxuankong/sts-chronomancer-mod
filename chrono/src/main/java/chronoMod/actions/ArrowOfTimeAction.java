package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class ArrowOfTimeAction extends AbstractGameAction {
    private AbstractCard card;

    public ArrowOfTimeAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
    }

    public void update() {
        this.card.baseDamage *= 2;
        this.card.modifyCostForCombat(1);

        this.isDone = true;
    }
}
