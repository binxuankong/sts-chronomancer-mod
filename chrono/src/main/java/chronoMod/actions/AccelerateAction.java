package chronoMod.actions;

import chronoMod.patches.ChronoEnum;
import chronoMod.powers.RecallPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AccelerateAction extends AbstractGameAction {
    private AbstractPlayer p;

    public AccelerateAction() {
        this.p = AbstractDungeon.player;
    }

    public void update() {
        ArrayList<AbstractPower> powers = p.powers;
        Collections.sort(powers);
        Iterator var1 = powers.iterator();

        while(var1.hasNext()) {
            AbstractPower pow = (AbstractPower)var1.next();

            if (pow.type == ChronoEnum.RECALL) {
                RecallPower recall_pow = (RecallPower)pow;
                recall_pow.triggerRecall();
            }
        }

        this.isDone = true;
    }
}
