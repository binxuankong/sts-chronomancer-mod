package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class ArrowOfTimeAction extends AbstractGameAction {
    private UUID uuid;

    public ArrowOfTimeAction(UUID targetUUID, int costAmount) {
        this.setValues(this.target, this.source, costAmount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
        this.amount = costAmount;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.baseDamage *= 2;
            c.cost = this.amount;
            c.isCostModified = true;
        }

        this.isDone = true;
    }
}
