package chronoMod.actions;

import chronoMod.powers.JadePower;
import chronoMod.powers.WillpowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FortunesEndAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo damageInfo;

    public FortunesEndAction(AbstractPlayer p, AbstractMonster m, DamageInfo damageInfo) {
        this.p = p;
        this.m = m;
        this.damageInfo = damageInfo;
    }

    public void update() {
        int numberOfHits = 0;

        // Willpower
        if (!this.p.hasPower(WillpowerPower.POWER_ID)) {
            numberOfHits += 2;
        }

        this.addToBot(new GainJadeAction(2));
        AbstractPower jade = this.p.getPower(JadePower.POWER_ID);
        if (jade != null) {
            numberOfHits += jade.amount;
        }
        for (int i = 0; i < numberOfHits; i++) {
            this.addToBot(new DamageAction(this.m, this.damageInfo, AbstractGameAction.AttackEffect.FIRE));
        }

        this.isDone = true;
    }
}
