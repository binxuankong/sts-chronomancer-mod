package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MysticShiftAction extends AbstractGameAction {
    private AbstractPlayer p;

    public MysticShiftAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null,
                    DamageInfo.createDamageMatrix(this.p.currentBlock, true), DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        this.isDone = true;
    }
}
