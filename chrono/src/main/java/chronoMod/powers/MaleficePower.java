package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MaleficePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("Malefice");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int damage;
    private DamageInfo.DamageType damageType;
    private AbstractMonster target;
    private static int idOffset;

    public MaleficePower(AbstractCreature owner, int damage, DamageInfo.DamageType damageType, AbstractMonster monster) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.damage = damage;
        this.damageType = damageType;
        this.target = monster;
        this.updateDescription();
        this.loadRegion("minion");
        this.priority = 40;
    }

    @Override
    public void recallEffect () {
        this.addToBot(new DamageAction(this.target, new DamageInfo(this.owner, this.damage, this.damageType),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new MaleficePower(this.owner, this.damage, this.damageType, this.target);
    }
}
