package chronoMod.actions;

import chronoMod.patches.RecallEnum;
import chronoMod.powers.RecallPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class AccelerateAction extends AbstractGameAction {
    private AbstractPlayer p;

    public AccelerateAction() {
        this.p = AbstractDungeon.player;
    }

    public void update() {
        Iterator var1 = p.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower pow = (AbstractPower)var1.next();

            if (pow.type == RecallEnum.RECALL) {
                RecallPower recall_pow = (RecallPower)pow;
                recall_pow.triggerRecall();
            }
        }

        this.isDone = true;
    }
}
